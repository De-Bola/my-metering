import React, { useEffect, useState } from "react";
import CostChart from "./CostChart";
import MarketPricesChart from "./MarketPricesChart";
import { getCostData, getMarketPrice, getMeteringPoint} from "./../services/MarketPriceService";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import ErrorBoundary from "./ErrorBoundary";

const Dashboard = () => {
  const jwtToken = localStorage.getItem("jwt");

  const [meteringPoints, setMeteringPoints] = useState([]);
  const [selectedMeteringPointId, setSelectedMeteringPointId] = useState(null);

  const today = new Date();
  const oneYearAgo = new Date();
  oneYearAgo.setFullYear(oneYearAgo.getFullYear() - 1);

  const [endDate, setEndDate] = useState(today);
  const [startDate, setStartDate] = useState(oneYearAgo); 

  const [costData, setCostData] = useState(null);
  const [marketPriceData, setMarketPriceData] = useState(null);

  useEffect(() => {
    
    getMeteringPoint(jwtToken)
      .then((response) => {
        const points = response.data.data[1];
        setMeteringPoints(points);
        if (points && points.length > 0) {
          setSelectedMeteringPointId(points[0].meteringPointId);
        }
      })
      .catch((error) => {
        console.error("Error fetching metering points:", error);
      });
  }, [jwtToken]);

  useEffect(() => {
    const startDateTime = startDate.toISOString();
    const endDateTime = endDate.toISOString();
    if (selectedMeteringPointId) {
      getCostData(startDateTime, endDateTime, jwtToken, selectedMeteringPointId)
        .then((response) => {
          setCostData(response.data);
        })
        .catch((error) => {
          console.error("There was an error fetching the cost data!", error);
        });
    }

    getMarketPrice(startDateTime, endDateTime, jwtToken)
      .then((response) => {
        setMarketPriceData(response.data);
      })
      .catch((error) => {
        console.error(
          "There was an error fetching the market price data!",
          error
        );
      });
  }, [selectedMeteringPointId, startDate, endDate, jwtToken]);

  if (!meteringPoints.length) {
    return <div>Loading metering points...</div>;
  }

  return (
    <div>
      <h2>Dashboard</h2>
      <div style={{ marginBottom: "20px" }}>
        <div>
          <label htmlFor="meteringPointSelect">Select Metering Point: </label>
          <select
            id="meteringPointSelect"
            value={selectedMeteringPointId || ""}
            onChange={(e) => setSelectedMeteringPointId(Number(e.target.value))}
          >
            {meteringPoints.map((mp) => (
              <option key={mp.meteringPointId} value={mp.meteringPointId}>
                {mp.address} (ID: {mp.meteringPointId})
              </option>
            ))}
          </select>
        </div>
        <div style={{ marginTop: "10px" }}>
          <label htmlFor="startDate">Start Date: </label>
          <DatePicker
            id="startDate"
            selected={startDate}
            onChange={(date) => setStartDate(date)}
            dateFormat="yyyy-MM-dd"
          />
        </div>
        <div style={{ marginTop: "10px" }}>
          <label htmlFor="endDate">End Date: </label>
          <DatePicker
            id="endDate"
            selected={endDate}
            onChange={(date) => setEndDate(date)}
            dateFormat="yyyy-MM-dd"
          />
        </div>
      </div>
      <div>
        <h3>Cost Breakdown for Metering Point ID: {selectedMeteringPointId}</h3>
        {costData ? (
          <CostChart costData={costData} />
        ) : (
          <div>Loading cost data...</div>
        )}
      </div>
      <ErrorBoundary>
          {marketPriceData ? (
            <MarketPricesChart marketPriceData={marketPriceData} />
          ) : (
            <div>Loading market price data...</div>
          )}
        </ErrorBoundary>
    </div>
  );
};

export default Dashboard;
