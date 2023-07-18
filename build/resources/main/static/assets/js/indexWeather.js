const timeEl = document.getElementById('time');
const dateEl = document.getElementById('date');
const currentWeatherItemsEl = document.getElementById('current-weather-items');
const timezone = document.getElementById('time-zone');
const countryEl = document.getElementById('country');
const weatherForecastEl = document.getElementById('weather-forecast');
const currentTempEl = document.getElementById('current-temp');
const wIcon = document.getElementById('wIcon')
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

    dateEl.innerHTML = months[month] + ' ' + date+ '일 ' + days[day]

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
	
	
	wIcon.innerHTML = `<img src="http://openweathermap.org/img/wn/${data.daily[0].weather[0].icon}@4x.png"
                     alt="weather icon" class="w-icon" height="180" style="position: relative;  top: -20px; right:10px;" />`;

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




}