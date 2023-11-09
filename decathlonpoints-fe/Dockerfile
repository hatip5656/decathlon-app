# Base image
FROM node:14-alpine

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json to the container
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the entire app to the container
COPY . .

# Build the React app
RUN npm run build

# Expose the port that your React app listens on
EXPOSE 3000

# Set the command to run your React app
CMD ["npm", "start"]