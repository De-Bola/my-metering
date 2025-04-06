import React from 'react';
import ReactEcharts from 'echarts-for-react';

const MarketPricesChart = ({ marketPriceData }) => {
  const months = marketPriceData.data.map(point => {
    const date = new Date(point.fromDateTime);
    return date.toISOString().substring(0, 7); // yyyy-MM
  });

  const option = {
    title: {
      text: 'Market Prices'
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['EUR per MWh', 'EUR per MWh with VAT']
    },
    xAxis: {
      type: 'category',
      data: months
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: 'EUR per MWh',
        type: 'line',
        data: marketPriceData.data.map(point => point.eurPerMwh)
      },
      {
        name: 'EUR per MWh with VAT',
        type: 'line',
        data: marketPriceData.data.map(point => point.eurPerMwhWithVat)
      }
    ]
  };

  return <ReactEcharts option={option} style={{ height: '400px', width: '100%' }} />;
};

export default MarketPricesChart;
