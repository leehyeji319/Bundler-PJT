// import { BASE_URL } from "./BASE_URL";
const userId = 276;
const BASE_URL = `http://localhost:8080/api/users/${userId}`;
//이미지 업로드 - 한 개, 여러개
async function uploadImage(files) {
  const fileArr = Array.isArray(files) ? [...files] : [files];
  let fileUrls = [];
  try {
    for (let file of fileArr) {
      const formData = new FormData();
      formData.append("image", file);
      const res = await fetch(BASE_URL + "/image/uploadfile", {
        method: "POST",
        body: formData,
      });
      const json = await res.json();
      fileUrls.push(`${BASE_URL}/${json.filename}`);
    }
    if (fileUrls.length > 1) {
      return fileUrls.join(",");
    } else {
      return fileUrls[0];
    }
  } catch (error) {
    console.log(error.message);
  }
}

export { uploadImage };
