import axios from "axios";

// axios 객체 생성
export default axios.create({
  withCredentials: true,
  baseURL: "http://localhost:8080/",
  headers: {
    "Content-type": "application/json",
  },
});
