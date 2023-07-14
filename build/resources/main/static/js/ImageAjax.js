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

/* 이미지 업로드 */
$("input[name='uploadfile']").on("change", function(e) {

  // 이미지 존재시 삭제
		if($(".imgDeleteBtn").length > 0){
			deleteFile();
		}

  // 파일 업로드를 위한 필요한 변수 및 객체 초기화
  let formData = new FormData();
  let fileInput = $(this);
  let fileList = fileInput[0].files;
  let fileObj = fileList[0];

  // 파일 정보 출력
  console.log("fileList: ", fileList);
  console.log("fileObj : ", fileObj);
  console.log("fileName : ", fileObj.name);
  console.log("fileSize : ", fileObj.size);
  console.log("fileType(MimeType) : ", fileObj.type);

  // 파일 유효성 검사
  if(!fileCheck(fileObj.name, fileObj.size)){
    return false;
  }

  alert("통과");

  // FormData에 파일 추가
  for(let i = 0; i < fileList.length; i++){
    formData.append("uploadfile", fileList[i]);
  }

  // 파일 업로드 Ajax 요청
  $.ajax({
    url:'/uploadAjaxAction', // 서버로 보낼 url
    processData:false, // 서버로 전송할 데이터를 queryString 형태로 변환할 지 여부
    contentType: false, // 서버로 전송되는 데이터의 content-type
    data: formData, // 서버로 전송할 데이터
    type:'POST', // 서버 요청 타입(Get Post),
    dataType:'json',
    success : function(result){
	    		console.log(result);
          showUploadImage(result);
	    	},
	  error : function(result){
	    		alert("이미지 파일이 아닙니다.");
	    	}
  });
});

// 파일 업로드 확장자 및 용량 제한
let regex = new RegExp("(.*?)\.(jpg|png)$");
let maxSize = 1048576; // 1MB

function fileCheck(fileName, fileSize){
  if(fileSize >= maxSize){
    alert("파일 사이즈 초과");
    return false;
  }
  if(!regex.test(fileName)){
    alert("해당 종류의 파일은 업로드 할 수 없습니다.");
    return false;
  }
  return true;
}

// 이미지 출력
function showUploadImage(uploadResultArr){
  //전달받은 데이터 검증
  if(!uploadResultArr||uploadResultArr.length==0){return}

  let uploadResult=$("#uploadResult");
  let profileImage = $("#profileImage");

  let obj=uploadResultArr[0];

  let str="";

  let fileCallPath = obj.uploadPath.replace(/\\/g, '/') + "/s_" + obj.uuid + "_" + obj.fileName;
    str += "<div id='result_card'>";
		str += "<img src='/display?fileName=" + fileCallPath +"'>";
    str += "<div class='imgDeleteBtn' data-file='" + fileCallPath + "'></div>";
    str += "<input type='hidden' name='imageList[0].fileName' value='"+ obj.fileName +"'>";
    str += "<input type='hidden' name='imageList[0].uuid' value='"+ obj.uuid +"'>";
    str += "<input type='hidden' name='imageList[0].uploadPath' value='"+ obj.uploadPath +"'>";
    str += "</div>";

  uploadResult.append(str);
  profileImage.hide();

}

// 이미지 삭제 버튼 동작
	$("#uploadResult").on("click", ".imgDeleteBtn", function(e){
    e.preventDefault(); // 이벤트 전파 중지
		deleteFile();
	});


// 파일 삭제 메서드
	function deleteFile(){
		let targetFile = $(".imgDeleteBtn").data("file");	
		let targetDiv = $("#result_card");
    let profileImage = $("#profileImage");

    console.log("삭제 할 targetFile : " + targetFile);

    $.ajax({
			url: '/deleteFile',
			data : {fileName : targetFile},
			dataType : 'text',
			type : 'POST',
			success : function(result){
				console.log(result);
			
				targetDiv.remove();
				$("input[type='file']").val("");

        profileImage.show();
				
			},
			error : function(result){
				console.log(result);
				
				alert("파일을 삭제하지 못하였습니다.")
			}
    });
		
	}