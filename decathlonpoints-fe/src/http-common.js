import axios from "axios";

const baseURL = process.env.BASE_URL || "http://localhost:8080/api/v1/decathlon-point";

export default axios.create({
  baseURL: baseURL,
  headers: {
    "Content-type": "application/json"
  }
});