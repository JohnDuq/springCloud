FROM mysql:8
# Add Password ROOT
ENV MYSQL_ROOT_PASSWORD=123456
# Copy the file to the mysql container where it runs by default
COPY ./zipkin-mysql.sql /docker-entrypoint-initdb.d/
# Expose the port
EXPOSE 3306