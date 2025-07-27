# syntax=docker/dockerfile:1.7

ARG MAVEN_VERSION=3.9.9
ARG JDK_VERSION=21

# ---------- Build Stage ----------
FROM --platform=$BUILDPLATFORM maven:${MAVEN_VERSION}-eclipse-temurin-${JDK_VERSION} AS builder
LABEL stage=builder

WORKDIR /workspace

# Copia o projeto inteiro para dentro do build context
COPY ../.. .

# Garante que o jar será gerado apenas para este módulo (e suas dependências locais)
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn -pl bank-transfer-request-service -am -T1C -B clean package -DskipTests

# ---------- Healthcheck Stage ----------
FROM busybox:1.36.1-glibc AS probe

# ---------- Runtime Stage ----------
FROM gcr.io/distroless/java21-debian12:nonroot AS runtime
LABEL stage=runtime

WORKDIR /app

# Copia apenas o jar final do request-service
COPY --from=builder /workspace/bank-transfer-request-service/target/bank-transfer-request-service-*.jar ./app.jar

# Copia o wget para o healthcheck
COPY --from=probe /bin/wget /bin/wget

EXPOSE 8091

HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -qO- http://localhost:8091/actuator/health || exit 1

ENTRYPOINT [ "java", "-XX:MaxRAMPercentage=80", "-jar", "/app/app.jar" ]