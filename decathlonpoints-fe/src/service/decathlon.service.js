import http from "../http-common";

class DecathlonService {
  getAll() {
    return http.get();
  }

  get(id) {
    return http.get(`/${id}`);
  }

  create(data) {
    return http.post('',data);
  }

  calculate(data) {
    return http.post(`/calculate`, data);
  }


}

export default new DecathlonService();