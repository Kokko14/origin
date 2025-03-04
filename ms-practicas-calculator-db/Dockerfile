# ------------------------------------------------------------------------
#
# Imagen de microservicio - Telefonica
# 
# Directorio de ubicacion de todos los microservicio /opt/ms/ms-practicas-calculator-db
#
# docker build -f Dockerfile -t ms-practicas-calculator-db:1.0.0-qa .
#
# ------------------------------------------------------------------------

FROM openjdk:8
MAINTAINER devops@telefonica.com.ec

RUN mkdir -p /opt/ms/ms-practicas-calculator-db

WORKDIR /opt/ms/ms-practicas-calculator-db

# --------------------------------
# Copy scripts, packages & plugins
# --------------------------------
COPY target/ms-practicas-calculator-db-1.0.0.jar /opt/ms/ms-practicas-calculator-db/


#-----------------------
# Expose container ports
# ----------------------
EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -jar $JAVA_OPTS /opt/ms/ms-practicas-calculator-db/ms-practicas-calculator-db-1.0.0.jar" ]
