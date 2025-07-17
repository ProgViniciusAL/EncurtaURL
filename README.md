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

## Tecnologias utilizadas

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- PostgreSQL e H2 para testes locais
- ZXing (geração de QR Codes)
- JUnit 5 / Mockito

## Endpoints principais

### Encurtar URL
**POST** `/short`

**Body JSON**:
```json
{
  "originalUrl": "https://www.google.com"
  "customAlias": "Google",
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
  "redirectUrl": "http://localhost:8080/r/abc123"
  "urlQrCode": "QR Code in progress",
  "createdAt": "2025-07-12T13:15:00"
}
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
- Autenticação via JWT para gestão privada de links
- Geração de códigos QR referente aos links encurtados
