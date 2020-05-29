let baseURL = ''
switch (process.env.NODE_ENV) {
  case 'production':
    baseURL = 'http://localhost/api'
    break
  case 'development':
    baseURL = 'http://localhost/api'
    break
  default:
    baseURL = 'http://localhost/api'
    break
}

export default {
  baseURL
}
