FROM nginx:1.20.0-alpine

COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf.template
COPY build /usr/share/nginx/html
COPY /nginx/entrypoint.sh /
RUN chmod +x entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]

CMD ["nginx", "-g", "daemon off;"]
