# Build stage
FROM node:20-alpine AS build
WORKDIR /app

# Copy package files (includes package-lock.json)
COPY package*.json ./

# Install dependencies (npm ci is faster and more reliable with lock file)
RUN npm ci --legacy-peer-deps

# Copy source code
COPY . .

# Build Angular app for production
RUN npm run build

# Production stage
FROM nginx:alpine

# Copy built Angular app (Angular outputs to dist/, not build/)
COPY --from=build /app/dist/flight-reserve-frontend/browser /usr/share/nginx/html

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80 (different from backend's 8080)
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]
