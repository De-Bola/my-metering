import axios from "axios";

const GET_MARKET_PRICE_ENDPOINT = "http://localhost:8080/api/market-prices";
const GET_METERING_POINT_ENDPOINT = "http://localhost:8080/api/metering-points";

export const getMarketPrice = async (startDateTime, endDateTime, jwtToken) => {
  return axios.get(GET_MARKET_PRICE_ENDPOINT, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${jwtToken}`,
    },
    params: { startDateTime, endDateTime },
  });
};

export const getCostData = async (startDateTime, endDateTime, jwtToken, meteringPointId) => {
  return axios.get(GET_METERING_POINT_ENDPOINT + '/' + `${meteringPointId}` + '/costs', {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${jwtToken}`,
    },
    params: { startDateTime, endDateTime },
  });
};

export const getMeteringPoint = async (jwtToken) => {
  return axios.get(GET_METERING_POINT_ENDPOINT, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${jwtToken}`,
    },
  });
};
