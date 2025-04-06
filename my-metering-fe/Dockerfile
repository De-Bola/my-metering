# Stage 1: Build the frontend using Node
FROM node:22.14.0 AS builder
WORKDIR /app
# Copy package files and install dependencies
COPY package*.json ./
RUN npm install
# Copy the rest of the source code
COPY . .
# Build the Vite application (this produces a "dist" folder)
RUN npm run build

# Stage 2: Serve the built files using Nginx
FROM nginx:alpine
# Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*
# Copy built assets from builder stage into nginx folder
COPY --from=builder /app/dist /usr/share/nginx/html
# Expose port 80 to the outside world
EXPOSE 80
# Start Nginx when the container launches
CMD ["nginx", "-g", "daemon off;"]
