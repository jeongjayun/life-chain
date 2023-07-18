/* 2016-2019 급성심장정지 생존율&발생률 */

const survivalStopHeart = '/csv/survivalStopHeart.csv';
const occurrenceRate = '/csv/occurrenceRate.csv';

Promise.all([d3.csv(survivalStopHeart), d3.csv(occurrenceRate)]).then(function(data) {
  const survivalData = data[0];
  const occurrenceData = data[1];

  const survivalYearList = {};
  const occurrenceYearList = {};

  survivalData.forEach(function(data) {
    const year = data.year;
    const allSvr = parseFloat(data.allSvr);

    if (!survivalYearList[year]) {
      survivalYearList[year] = 0;
    }

    survivalYearList[year] += allSvr;
  });

  occurrenceData.forEach(function(data) {
    const year = data.year;
    const allOccr = parseFloat(data.allOccr);

    if (!occurrenceYearList[year]) {
      occurrenceYearList[year] = 0;
    }

    occurrenceYearList[year] += allOccr;
  });

  const years = Object.keys(survivalYearList);
  const survivalRates = Object.values(survivalYearList);
  const occurrenceRates = Object.values(occurrenceYearList);

  const ctx = document.getElementById('comparisonChart');
  
const topLabels = {
  id: 'topLabels',
  afterDatasetsDraw(chart, args, pluginOptions) {
    const { ctx, scales: { x, y } } = chart;

    chart.data.datasets[0].data.forEach((datapoint, index) => {
      const datasetArray = [];

      chart.data.datasets.forEach((dataset) => {
        datasetArray.push(dataset.data[index]);
      });

	function totalSum(total, values) {
	  return (datasetArray[1] / datasetArray[0]) * 100;
	}

      let sum = datasetArray.reduce(totalSum, 0);

      ctx.font = 'bold 12px';
      ctx.fillStyle = 'rgba(255, 26, 104, 1)';
      ctx.fillText(sum.toFixed(1)+'%', x.getPixelForValue(index), chart.getDatasetMeta(1).data[index].y - 10);
    });
  }
};

  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: years,
      datasets: [
        {
          label: '발생률',
          data: occurrenceRates,
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 1.5,
          borderRadius: 50
        },
        {
          label: '생존율',
          data: survivalRates,
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
          borderColor: 'rgba(75, 192, 192, 1)',
          borderWidth: 1.5,
          borderRadius: 50
        }
      ]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    },
    plugins: [ChartDataLabels, topLabels]
  });
});


/* 2016-2019 급성심장정지 생존율&발생률 */

const survivalStopHeart2 = '/csv/survivalStopHeart.csv';
const recoveryBrain2 = '/csv/recoveryBrain.csv';

Promise.all([d3.csv(survivalStopHeart2), d3.csv(recoveryBrain2)]).then(function(data) {
  const survivalData = data[0];
  const recoveryData = data[1];

  const survivalYearList = {};
  const recoveryYearList = {};

  survivalData.forEach(function(data) {
    const year = data.year;
    const allSvr = parseFloat(data.allSvr);

    if (!survivalYearList[year]) {
      survivalYearList[year] = 0;
    }

    survivalYearList[year] += allSvr;
  });

  recoveryData.forEach(function(data) {
    const year = data.year;
    const allBrfr = parseFloat(data.allBrfr);

    if (!recoveryYearList[year]) {
      recoveryYearList[year] = 0;
    }

    recoveryYearList[year] += allBrfr;
  });

  const years = Object.keys(survivalYearList);
  const survivalRates = Object.values(survivalYearList);
  const recoveryRates = Object.values(recoveryYearList);

  const ctx = document.getElementById('cat');
  
const topLabels = {
  id: 'topLabels',
  afterDatasetsDraw(chart, args, pluginOptions) {
    const { ctx, scales: { x, y } } = chart;

    chart.data.datasets[0].data.forEach((datapoint, index) => {
      const datasetArray = [];

      chart.data.datasets.forEach((dataset) => {
        datasetArray.push(dataset.data[index]);
      });

	function totalSum(total, values) {
	  return (datasetArray[1] / datasetArray[0]) * 100;
	}
      let sum = datasetArray.reduce(totalSum, 0);

      ctx.font = 'bold 12px';
      ctx.fillStyle = 'rgba(255, 26, 104, 1)';
      ctx.fillText(sum.toFixed(1)+'%', x.getPixelForValue(index), chart.getDatasetMeta(1).data[index].y - 10);
    });
  }
};

  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: years,
      datasets: [
              {
          label: '생존율',
          data: survivalRates,
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
          borderColor: 'rgba(75, 192, 192, 1)',
          borderWidth: 1.5,
          borderRadius: 50
        },
        {
          label: '회복율',
          data: recoveryRates,
          backgroundColor: 'rgba(153, 102, 255, 0.2)',
          borderColor: 'rgba(153, 102, 255, 1)',
          borderWidth: 1.5,
          borderRadius: 50
        }
      ]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    },
    plugins: [ChartDataLabels, topLabels]
  });
});

/*2016-2019 급성심장정지 일반인*/

const tryGeneralPerson = '/csv/tryGeneralPerson.csv';

d3.csv(tryGeneralPerson).then(function(datapoints) {
  const YearList = {};

  datapoints.forEach(function(data) {
    const year = data.year;
    const dsPbcpr = parseFloat(data.dsPbcpr);
    const othdsPbcpr = data.othdsPbcpr !== null ? parseFloat(data.othdsPbcpr) : 0;

    if (!YearList[year]) {
      YearList[year] = {
        dsPbcpr: dsPbcpr,
        othdsPbcpr: othdsPbcpr
      };
    } else {
      YearList[year].dsPbcpr += dsPbcpr;
      YearList[year].othdsPbcpr += othdsPbcpr;
    }
  });

  const years = Object.keys(YearList);
  const dsPbcprs = Object.values(YearList).map(data => data.dsPbcpr);
  const othdsPbcprs = Object.values(YearList).map(data => data.othdsPbcpr);

  const ctx = document.getElementById('tryGeneralPerson');
  
  const topLabels = {
	id: 'topLabels',
	afterDatasetsDraw(chart, args, pluginOptions){
		const{ctx, scales: {x,y}}= chart;
		
		chart.data.datasets[0].data.forEach((datapoint, index) => {
			const datasetArray = [];
			
			chart.data.datasets.forEach((dataset) => {
				datasetArray.push(dataset.data[index])
			})
			
				function totalSum(total, values) {
				return total + values;
			};
			let sum = datasetArray.reduce(totalSum, 0)
			
		ctx.font = 'bold 12px';
		ctx.fillStyle = 'rgba(255, 26, 104, 1)';
		ctx.textAlign = 'center';
		ctx.fillText(sum, x.getPixelForValue(index), chart.getDatasetMeta(1).data[index].y -10);
		})
	}
   }

  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: years,
      datasets: [
        {
          label: '질병',
          data: dsPbcprs,
          backgroundColor: 'rgba(255, 159, 64, 0.2)',
          borderColor: 'rgba(255, 159, 64, 1)',
          borderWidth: 1.5,
          borderRadius: 50
        },
        {
          label: '질병 외',
          data: othdsPbcprs,
          borderColor: 'rgba(0, 0, 0, 0.2)',
          borderColor: 'rgba(0, 0, 0, 1)',
          borderWidth: 1.5,
          borderRadius: 50
        }
      ]
    },
    
    options: {
      scales: {
        x: {
          stacked: true,
        },
        y: {
          stacked: true,
          beginAtZero: true
        }
      }
    },
    plugins: [ChartDataLabels, topLabels]
  });
});

/* Font Size */

  window.onload = function(){
	  //console.log(window.innerWidth)
	  if(window.innerWidth >= 999){
		  Chart.defaults.font.size = 16;
	  }else if(window.innerWidth >= 666 && window.innerWidth < 999){
		  Chart.defaults.font.size = 14;
	  } else {
		  Chart.defaults.font.size = 12;
	  }
  };