FROM node:lts-alpine3.18
WORKDIR /app
COPY package.json ./
COPY vite.config.js ./
COPY yarn.lock ./
RUN yarn install
COPY . .
EXPOSE 5173
CMD [ "yarn", "start" ]