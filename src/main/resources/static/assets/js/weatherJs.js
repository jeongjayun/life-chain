const timeEl = document.getElementById('time');
const dateEl = document.getElementById('date');
const currentWeatherItemsEl = document.getElementById('current-weather-items');
const timezone = document.getElementById('time-zone');
const countryEl = document.getElementById('country');
const weatherForecastEl = document.getElementById('weather-forecast');
const currentTempEl = document.getElementById('current-temp');

const manual = document.getElementById('manual');

const days = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일']
const months = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

const API_KEY ='d040a2a3d9224c924f0c8a85b89dde6e';

setInterval(() => {
    const time = new Date();
    const month = time.getMonth();
    const date = time.getDate();
    const day = time.getDay();
    const hour = time.getHours();
    const hoursIn12HrFormat = hour >= 13 ? hour %12: hour
    const minutes = time.getMinutes();
    const ampm = hour >=12 ? '오후' : '오전'

    timeEl.innerHTML = (hoursIn12HrFormat < 10? '0'+hoursIn12HrFormat : hoursIn12HrFormat) + ':' + (minutes < 10? '0'+minutes: minutes)+ ' ' + `<span id="am-pm">${ampm}</span>`

    dateEl.innerHTML = months[month] + date+ '일 ' + days[day]

}, 1000);

getWeatherData()
function getWeatherData () {
    navigator.geolocation.getCurrentPosition((success) => {
        
        let {latitude, longitude } = success.coords;

        fetch(`https://api.openweathermap.org/data/2.5/onecall?lat=${latitude}&lon=${longitude}&exclude=hourly,minutely&appid=${API_KEY}&units=metric`).then(res => res.json()).then(data => {

        console.log(data)
        showWeatherData(data);
        })

    })
}

function showWeatherData (data){
    let {humidity, pressure, sunrise, sunset, wind_speed, temp} = data.current;

    timezone.innerHTML = data.timezone;
    countryEl.innerHTML = '위도 ' + data.lat + ' 경도 ' + data.lon

    currentWeatherItemsEl.innerHTML = 
    `<div class="weather-item">
        <div>현재 습도</div>
        <div>${humidity}%</div>
    </div>
    <div class="weather-item">
        <div>현재 기온</div>
        <div>${Math.round(temp)}&#176; C</div>
    </div>
    <div class="weather-item">
        <div>기압</div>
        <div>${pressure}</div>
    </div>
    <div class="weather-item">
        <div>풍속</div>
        <div>${wind_speed}</div>
    </div>

    <div class="weather-item">
        <div>일출시간</div>
        <div>${window.moment(sunrise * 1000).format('HH:mm a')}</div>
    </div>
    <div class="weather-item">
        <div>일몰시간</div>
        <div>${window.moment(sunset*1000).format('HH:mm a')}</div>
    </div>
    
    
    `;
    
    const weatherDescription = data.daily[0].weather[0].description;
	let outputMessage;
	
	if (weatherDescription === "clear sky") {
	  outputMessage = "맑은 하늘입니다.";
	  
    } else if (weatherDescription === "light rain") {
	  outputMessage = "가벼운 비가 내리고 있습니다.";  
    } else if (weatherDescription === "moderate rain") {
	  outputMessage = "비가 내리고 있습니다.";  
    } else if (weatherDescription === "heavy intensity rain") {
	  outputMessage = "강한 비가 내리고 있습니다.";  
    } else if (weatherDescription === "very heavy rain") {
	  outputMessage = "집중폭우가 내리고 있습니다.";  
    } else if (weatherDescription === "extreme rain") {
	  outputMessage = "폭우가 내리고 있습니다.";  
    } else if (weatherDescription === "freezing rain") {
	  outputMessage = "우빙이 오고 있습니다.";  
    } else if (weatherDescription === "light intensity shower rain") {
	  outputMessage = "가벼운 소나기가 내리고 있습니다.";  
    } else if (weatherDescription === "shower rain") {
	  outputMessage = "소나기가 내리고 있습니다.";
	} else if (weatherDescription === "heavy intensity shower rain") {
	  outputMessage = "집중 호우가 내리고 있습니다."; 
	} else if (weatherDescription === "ragged shower rain") {
	  outputMessage = "불규칙한 소나기가 내리고 있습니다."; 
	  
	} else if (weatherDescription === "few clouds") {
	  outputMessage = "구름이 조금 있습니다.";
	} else if (weatherDescription === "scattered clouds") {
	  outputMessage = "산 scattered clouds입니다.";
	} else if (weatherDescription === "broken clouds") {
	  outputMessage = "적란운이 있습니다.";
	} else if (weatherDescription === "rain") {
	  outputMessage = "비가 내리고 있습니다.";
	} else if (weatherDescription === "thunderstorm") {
	  outputMessage = "천둥번개가 치고 있습니다.";
	} else if (weatherDescription === "snow") {
	  outputMessage = "눈이 내리고 있습니다.";
	} else if (weatherDescription === "mist") {
	  outputMessage = "안개가 짙습니다.";
	} else {
	  outputMessage = "날씨 정보를 찾을 수 없습니다.";
	}
	
	manual.innerHTML = `<p>${outputMessage}</p>`;
	
    

    	
    //한파
	for (let key in data) {
	  if (data.hasOwnProperty(key)) {
	    const item = data[key];
	    const monthIndex = new Date(item.date).getMonth();
	    const month = months[monthIndex];
	
	    // 10월~4월 사이의 기간 동안
	    if (monthIndex >= 9 || monthIndex <= 3) {
			// 아침 최저기온이 전날보다 10℃ 이상 하강하여 3℃ 이하.
			(item.daily[0].temp.min - item.daily[1].temp.min) >= 10 && item.daily[0].temp.min <= 3

	      	// 아침 최저기온이 -12℃ 이하가 2일 이상 지속.
	      	(item.daily[0].temp.min <= -12) && (item.daily[1].temp.min <= -12);
	      	manual.innerHTML = `<p>한파 주의보입니다.</p>`
	    }
	  }
	}
	
	//강풍
    if(data.daily.wind_speed>=14){
	manual.innerHTML = `<p>강풍 주의보입니다.</p>`
	}



	let otherDayForcast = '';
	data.daily.forEach((day, idx) => {
	    if (idx == 0) {
	        currentTempEl.innerHTML = `
	        <img src="http://openweathermap.org/img/wn/${day.weather[0].icon}@4x.png" alt="weather icon" class="w-icon">
	        <div class="other">
	            <div class="day">${window.moment(day.dt * 1000).locale('ko').format('dddd')}</div>
	            <div class="temp">낮 - ${Math.round(day.temp.day)}&#176;C</div>
	            <div class="temp">밤 - ${Math.round(day.temp.night)}&#176;C</div>
	        </div>
	        `;
	    } else {
	        otherDayForcast += `
	        <div class="weather-forecast-item">
	            <div class="day">${window.moment(day.dt * 1000).locale('ko').format('dddd')}</div>
	            <img src="http://openweathermap.org/img/wn/${day.weather[0].icon}@2x.png" alt="weather icon" class="w-icon">
	            <div class="temp">낮 - ${Math.round(day.temp.day)}&#176;C</div>
	            <div class="temp">밤 - ${Math.round(day.temp.night)}&#176;C</div>
	        </div>
	        `;
	    }
	});


    weatherForecastEl.innerHTML = otherDayForcast;
}