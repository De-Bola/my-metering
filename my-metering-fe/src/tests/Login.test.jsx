import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import Login from "../components/Login";
import CustomerService from "../services/CustomerService";
import { BrowserRouter } from "react-router-dom";
import { vi } from "vitest";
import { beforeEach, afterEach, describe, test, expect } from "vitest";

// Mock the CustomerService.login function.
vi.mock("../services/CustomerService", () => ({
    login: vi.fn(),
    return : {
        login: vi.fn((username, password) => {
            return new Promise((resolve, reject) => {
            if (username === "testuser" && password === "testpass") {
                resolve({
                data: {
                    data: {
                    token: "mock-jwt-token",
                    response: { customerId: "1", firstName: "Test", lastName: "User", username: "testuser" },
                    },
                    message: "Login successful!",
                    code: "200 OK",
                },
                });
            } else {
                reject(new Error("Invalid credentials"));
            }
            });
        }),
    },
  }));

describe("Login Component", () => {
  test("successful login stores token and navigates to dashboard", async () => {
    // Arrange: simulate a successful login response.
    const mockResponse = {
      data: {
        data: {
          token: "mock-jwt-token",
          response: { customerId: "1", firstName: "Test", lastName: "User", username: "testuser" },
        },
        message: "Login successful!",
        code: "200 OK",
      },
    };
    CustomerService.login.mockResolvedValue(mockResponse);

    // Render the Login component wrapped in BrowserRouter (for useNavigate)
    render(
      <BrowserRouter>
        <Login />
      </BrowserRouter>
    );

    // Act: Fill in the login form and submit.
    const usernameInput = screen.getByPlaceholderText("Username");
    const passwordInput = screen.getByPlaceholderText("Password");
    const loginButton = screen.getByRole("button", { name: /login/i });

    fireEvent.change(usernameInput, { target: { value: "testuser" } });
    fireEvent.change(passwordInput, { target: { value: "testpass" } });
    fireEvent.click(loginButton);

    // Assert: Wait for login success and redirection.
    await waitFor(() => {
      expect(CustomerService.login).toHaveBeenCalledWith("testuser", "testpass");
    });
    // Verify that the JWT is stored.
    expect(localStorage.getItem("jwt")).toBe("mock-jwt-token");
    // Optionally, you might verify navigation by mocking useNavigate.
  });
});
