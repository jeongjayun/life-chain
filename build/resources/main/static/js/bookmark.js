/*<![CDATA[*/
// CSRF 토큰 가져오기
function getCsrfToken() {
  return $("meta[name='_csrf']").attr("content");
}

// CSRF 헤더 이름 가져오기
function getCsrfHeader() {
  return $("meta[name='_csrf_header']").attr("content");
}

console.log("CSRF 토큰: ", getCsrfToken());
console.log("CSRF 헤더: ", getCsrfHeader());

/*// Ajax 요청 전에 CSRF 토큰을 설정
$.ajaxSetup({
  beforeSend: function(xhr) {
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());
  }
});*/

// 세션에서 회원 ID를 가져오는 함수
function getMemberIdFromSession() {
  const memberId = sessionStorage.getItem('memberId'); // 세션에서 회원 ID를 가져오는 코드
  return memberId;
}
// 회원 ID를 가져오는 함수
function getMemberId() {
  // 세션을 사용하여 회원 ID를 가져오는 함수 호출
  const memberId = getMemberIdFromSession();
  return memberId;
}

function toggleBookmark(id) {
  const isChecked = $('#like-toggle-' + id).prop('checked');
  const url = isChecked ? `/bookmark/add/${id}` : `/bookmark/remove/${id}`;

  const bookmarkDto = {
    memberId: getMemberId(), // 동적으로 회원 ID를 가져와서 설정
    manualId: id
  };

  $.ajax({
    type: isChecked ? 'POST' : 'DELETE',
    url: url,
    contentType: 'application/json',
    data: JSON.stringify(bookmarkDto), // 요청 바디에 데이터 전송
    beforeSend: function (xhr) {
      xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());
    },
    success: function (response) {
      console.log(response); // 성공적인 응답 처리
    },
    error: function (xhr, status, error) {
      console.log("에러 상태:", status); // 에러 상태 코드 출력
      console.log("에러 메시지:", error); // 에러 메시지 출력
      console.log("XHR 객체:", xhr); // XHR 객체 정보 출력
    }
  });
}
/*]]>*/


/*function toggleBookmark(id) {
  const isChecked = $('#like-toggle-' + id).prop('checked');
  const url = isChecked ? `/bookmark/add/${id}` : `/bookmark/remove/${id}`;

  $.ajax({
    type: isChecked ? 'POST' : 'DELETE',
    url: url,
    contentType: 'application/json',
    data: JSON.stringify(bookmarkDto), // 요청 바디에 데이터 전송
    beforeSend: function (xhr) {
      xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());
    },
    success: function (response) {
      console.log(response); // 성공적인 응답 처리
    },
    error: function (xhr, status, error) {
      console.log("에러 상태:", status); // 에러 상태 코드 출력
      console.log("에러 메시지:", error); // 에러 메시지 출력
      console.log("XHR 객체:", xhr); // XHR 객체 정보 출력
    }
  });
}*/


/*
// CSRF 토큰 가져오기
function getCsrfToken() {
  return $("meta[name='_csrf']").attr("content");
}

// CSRF 헤더 이름 가져오기
function getCsrfHeader() {
  return $("meta[name='_csrf_header']").attr("content");
}

console.log("CSRF 토큰: ", getCsrfToken());
console.log("CSRF 헤더: ", getCsrfHeader());

// Ajax 요청 전에 CSRF 토큰을 설정
$.ajaxSetup({
  beforeSend: function(xhr) {
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());
  }
});

function toggleBookmark(id) {
  const isChecked = $('#like-toggle-'+id).prop('checked');
  const url = isChecked ? `/bookmark/add/${id}` : `/bookmark/remove/${id}`;

  $.ajax({
    type: isChecked ? 'POST' : 'DELETE',
    url: url,
    contentType: 'application/json',
    success: function (response) {
      console.log(response); // 성공적인 응답 처리
    },
    error: function (xhr, status, error) {
      console.error(error); // 오류 처리
    }
  });
}
*/


/*
<!--function toggleBookmark(id) {-->
<!--    const isChecked = document.getElementById(`like-toggle-${id}`).checked;-->

<!--    console.log(isChecked);-->
<!--    const url = `/bookmark/${id}`;-->
<!--    if (isChecked) {-->
<!--      fetch(url, {-->
<!--        method: 'POST',-->
<!--        headers: {-->
<!--          'Content-Type': 'application/json',-->
<!--        },-->
<!--        body: JSON.stringify({ id }),-->
<!--      })-->
<!--        .then(response => {-->
<!--          // 응답을 처리하는 코드 작성-->
<!--        if (!response.ok) {-->
<!--        throw new Error(`${response.status} ${response.statusText}`);-->
<!--	      }-->
<!--	      return response.json(); // JSON 형태로 변환하여 반환-->
<!--	    })-->
<!--	    .then(result => {-->
<!--	      console.log(result); // 가공된 응답 데이터 출력-->
<!--	    })-->
<!--        .catch(error => {-->
<!--          console.error(error);-->
<!--        });-->
<!--    } else {-->
<!--      // 북마크 삭제 요청 코드 작성-->
<!--      fetch(`${url}/${id}`, {-->
<!--        method: 'DELETE',-->
<!--        headers: {-->
<!--          'Content-Type': 'application/json',-->
<!--        },-->
<!--      })-->
<!--        .then(response => {-->
<!--          // 응답을 처리하는 코드 작성-->
<!--        })-->
<!--        .catch(error => {-->
<!--          console.error(error);-->
<!--        });-->
<!--    }-->
<!--}-->*/