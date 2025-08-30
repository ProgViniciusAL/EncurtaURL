# Encurtador de URL - Spring Boot

Este projeto é uma API REST desenvolvida com Spring Boot para encurtamento de URLs. Ela permite transformar URLs longas em versões curtas, rastrear acessos e gerar QR Codes a partir dos links encurtados.

## Funcionalidades

- Encurtamento automático de URLs com código gerado
- Associação de nome/descritivo à URL
- Redirecionamento por código encurtado
- Validação de URL de entrada
- Contador de acessos
- Geração de QR Code para URLs (em desenvolvimento)
- Testes unitários com JUnit e Mockito
- Integração com banco de dados via Spring Data JPA
- Integração com Docker para hospedagem com uma imagem pública (Docker Hub)

## Tecnologias utilizadas

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- PostgreSQL e H2 para testes locais
- ZXing (geração de QR Codes)
- JUnit 5 / Mockito
- Docker

## Hospedagem
#### API: (Render) https://encurta-url-image-1-0.onrender.com
#### Banco de dados: Aiven.io

## Endpoints principais

### Registro de usuário
**POST** `/auth/register`

**Body JSON**
```json
{
  "username": "string",
  "password": "string",
  "email": "string",
}
```
**Resposta**:

```json
{
  "username": "string",
  "email": "string"
}
```

### Login de usuário

**POST** `/auth/login`

**Body JSON**
```json
{
  "password": "string",
  "email": "string"
}
```
**Resposta**:

```json
{
  "token": "string"
}
```

### Encurtar URL
**POST** `/short`

**Body JSON**:
```json
{
  "originalUrl": "https://www.google.com",
  "customAlias": "Google"
}
```
**Resposta**:

```json
{
  "id": "uuid",
  "customAlias": "Google",
  "clickCount": 0,
  "longUrl": "https://www.google.com",
  "shortUrl": "abc123",
  "redirectUrl": "http://localhost:8080/r/abc123",
  "urlQrCode": "QR Code em formato base64",
  "createdAt": "2025-07-12T13:15:00"
}
```

### Gerar QR Code da URL
**GET** `/url/{id}/qrcode`

**Resposta**:
```
Imagem do QR Code em formato PNG
```

### Recuperar URL's  do usuário
**GET** `/links`

**Resposta**:
```json
[
  {
    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "alias": "string",
    "clickCount": 0,
    "longUrl": "string",
    "shortUrl": "string",
    "shortCode": "string",
    "QRCode": [
      "string"
    ],
    "createdAt": "2025-08-03T23:08:28.785Z"
  }
]
```

## Redirecionamento
**GET** `/r/{shortUrl}`

Redireciona automaticamente para a URL original e incrementa o contador de cliques.

## Validação de URL

A aplicação rejeita URLs malformadas por meio de validação utilizando **java.net.URL**. URLs inválidas resultam em **400 Bad Request**.

## Testes

Os testes estão escritos com **JUnit 5** e **Mockito**, focando principalmente na camada de serviço. Para rodá-los:
```

bash ./mvnw test

```

## Futuras melhorias
- Expiração de links
- Interface web para encurtar e visualizar estatísticas
- Integração com Redis para cache de redirecionamentos
