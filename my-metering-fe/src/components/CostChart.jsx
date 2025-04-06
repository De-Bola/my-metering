import React from 'react';
import ReactEcharts from 'echarts-for-react';

const CostChart = ({ costData }) => {
  const option = {
    title: {
      text: 'Monthly Cost Breakdown'
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['Total Consumption', 'Market Price', 'Monthly Cost']
    },
    xAxis: {
      type: 'category',
      data: costData.monthlyCosts.map(item => item.month)
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: 'Total Consumption',
        type: 'line',
        data: costData.monthlyCosts.map(item => item.totalConsumption)
      },
      {
        name: 'Market Price',
        type: 'line',
        data: costData.monthlyCosts.map(item => item.marketPrice)
      },
      {
        name: 'Monthly Cost',
        type: 'line',
        data: costData.monthlyCosts.map(item => item.monthlyCost)
      }
    ]
  };

  return <ReactEcharts option={option} style={{ height: '400px', width: '100%' }} />;
};

export default CostChart;
