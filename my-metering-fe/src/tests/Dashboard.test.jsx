import React from "react";
import { render, screen, waitFor, fireEvent } from "@testing-library/react";
import Dashboard from "../components/Dashboard";
import * as MarketPriceService from "../services/MarketPriceService";
import { vi } from "vitest";
import { beforeEach, afterEach, describe, test, expect } from "vitest";

const mockMeteringPointsResponse = {
  data: ["java.util.ArrayList", [
    { meteringPointId: 1, address: "Home" },
    { meteringPointId: 2, address: "Church Office" }
  ]],
  message: "Found 2 metering point(s)",
  code: "200 OK"
};

const mockCostData = {
  monthlyCosts: [
    { month: "2024-04", totalConsumption: 400, marketPrice: 80, monthlyCost: 32000 },
    { month: "2024-05", totalConsumption: 500, marketPrice: 85, monthlyCost: 42500 }
  ]
};

const mockMarketPriceData = {
  "@class": "com.enefit.metering.models.MarketPriceData",
  data: [
    {
      centsPerKwh: 8.74,
      centsPerKwhWithVat: 10.67,
      eurPerMwh: 87.44,
      eurPerMwhWithVat: 106.68,
      fromDateTime: "2024-04-30T21:00:00Z",
      toDateTime: "2024-05-31T20:59:59.999999999Z"
    },
    {
      centsPerKwh: 9.37,
      centsPerKwhWithVat: 11.44,
      eurPerMwh: 93.74,
      eurPerMwhWithVat: 114.36,
      fromDateTime: "2024-05-31T21:00:00Z",
      toDateTime: "2024-06-30T20:59:59.999999999Z"
    }
  ]
};

vi.mock("../services/MarketPriceService", () => ({
  getMeteringPoint: vi.fn(),
  getCostData: vi.fn(),
  getMarketPrice: vi.fn(),
}));

// Set up a mock JWT token in localStorage for our tests.
beforeEach(() => {
  localStorage.setItem("jwt", "mock-jwt-token");
});

afterEach(() => {
  localStorage.clear();
});

describe("Dashboard", () => {
  test("renders dashboard with metering points, cost and market price data", async () => {
    // Mock the API calls for metering points, cost data, and market prices.
    MarketPriceService.getMeteringPoint.mockResolvedValue(mockMeteringPointsResponse);
    MarketPriceService.getCostData.mockResolvedValue({ data: mockCostData });
    MarketPriceService.getMarketPrice.mockResolvedValue({ data: mockMarketPriceData });

    render(<Dashboard />);

    // Wait for metering points to be loaded.
    await waitFor(() => {
      expect(screen.getByLabelText(/Select Metering Point/i)).toBeInTheDocument();
    });

    // Check if dropdown contains an option from mockMeteringPointsResponse.
    expect(screen.getByText(/Home \(ID: 1\)/)).toBeInTheDocument();

    // Wait for cost chart to be rendered.
    await waitFor(() => {
      expect(screen.getByText(/Cost Breakdown for Metering Point ID: 1/i)).toBeInTheDocument();
    });

    // Optionally, you can check that the MarketPricesChart component is rendered.
    expect(screen.getByText(/Market Prices/i)).toBeInTheDocument();
  });

  test("updates cost data when a different metering point is selected", async () => {
    // Set up two different metering points.
    const pointsResponse = {
      data: ["java.util.ArrayList", [
        { meteringPointId: 1, address: "Home" },
        { meteringPointId: 2, address: "Church Office" }
      ]],
      message: "Found 2 metering point(s)",
      code: "200 OK"
    };
    MarketPriceService.getMeteringPoint.mockResolvedValue(pointsResponse);
    // Initially, for meteringPointId 1.
    MarketPriceService.getCostData.mockResolvedValueOnce({ data: mockCostData });
    // Then, for meteringPointId 2, return different cost data.
    const newMockCostData = {
      monthlyCosts: [
        { month: "2024-04", totalConsumption: 300, marketPrice: 75, monthlyCost: 22500 },
        { month: "2024-05", totalConsumption: 450, marketPrice: 80, monthlyCost: 36000 }
      ]
    };
    MarketPriceService.getCostData.mockResolvedValueOnce({ data: newMockCostData });
    MarketPriceService.getMarketPrice.mockResolvedValue({ data: mockMarketPriceData });

    render(<Dashboard />);

    // Wait for the dropdown to appear.
    const dropdown = await screen.findByLabelText(/Select Metering Point/i);
    // Initially, cost data for meteringPointId 1 is loaded.
    expect(screen.getByText(/Cost Breakdown for Metering Point ID: 1/i)).toBeInTheDocument();

    // Change the dropdown value to meteringPointId 2.
    fireEvent.change(dropdown, { target: { value: "2" } });

    await waitFor(() => {
      expect(screen.getByText(/Cost Breakdown for Metering Point ID: 2/i)).toBeInTheDocument();
    });
  });
});
