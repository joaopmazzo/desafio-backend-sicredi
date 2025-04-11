
# Desafio Backend Sicredi

Este projeto é uma solução para o desafio backend proposto pela Sicredi. Ele implementa funcionalidades específicas para atender aos requisitos do desafio, utilizando boas práticas de desenvolvimento e arquitetura de software.

## Objetivos

- [x] **Cadastrar uma nova pauta**
- [x] **Abrir uma sessão de votação em uma pauta**
- [x] **Receber votos dos associados em pautas**
- [x] **Contabilizar os votos e fornecer o resultado da votação**

### Bônus
- [ ] **Integrar com sistema externo:**
- [x] **Mensageria e filas:**
- [x] **Testes de Performance:**
- [x] **Versionamento da API:** Usaria a estratégia de versionamento já adotada neste projeto, onde caso eu precise realizar uma alteração no endpoint que seja um break change para os usuarios, criaria uma nova versão para que as partes do sistema que utilizem nao quebrem. 

## Tecnologias Utilizadas

- **Linguagem:** Java 17
- **Frameworks:** Spring Boot
- **Bibliotecas e Módulos:** Spring Web, Spring Data JPA, Spring AMQP, Spring Quartz, Spring Validation, Spring Test, SpringDoc OpenAPI, MapStruct, Lombok
- **Banco de Dados:** PostgreSQL, H2 (para testes)
- **Migrations:** Flyway
- **Outras Ferramentas:** Docker

## Arquitetura do Projeto

O projeto segue uma arquitetura limpa e bem organizada, com separação clara entre as camadas:

- **Application:** Contém os DTOs, mapeamento de exceptions, casos de uso e controllers da aplicação.
- **Domain:** Contém as entidades, enums, repositórios e a camada de serviço.
- **Infrastructure:** Contém as configurações de mensageria (RabbitMQ), agendadores (Quartz) e mappers.

## Funcionalidades

- **Mensageria com RabbitMQ:**
    - O `ResultadoSessaoProducer` envia mensagens para a fila configurada.
    - O `ResultadoSessaoConsumer` escuta mensagens na fila, no código coloquei apenas um log para representar a captura da informação, mas pode entrar qualquer lógica julgada necessária, como por exemplo disparo de email ou trigger de websocket para plataforma.
- **Scheduler com Quartz:**
    - Um agendador verifica periodicamente as sessões de votação que passaram do prazo de finalização, finaliza-as e envia os resultados para o RabbitMQ.
- **API RESTful:**
    - Endpoints para gerenciar pautas, sessões e votos.
      - pautas:
        - `POST /api/v1/pautas`: Cadastrar uma nova pauta.
        - `GET /api/v1/pautas`: Listar todas as pautas.
        - `GET /api/v1/pautas/{id}`: Obter detalhes de uma pauta específica.
      - sessões:
        - `POST /api/v1/pautas/{id}/sessao`: Abrir uma sessão de votação para uma pauta.
        - `GET /api/v1/pautas/{id}/sessao`: Obter detalhes da sessão de votação de uma pauta.
        - `GET /api/v1/sessoes`: Listar todas as sessões de votação.
      - votos:
        - `POST /api/v1/pautas/{id}/voto`: Registrar um voto em uma pauta.
        - `GET /api/v1/pautas/{id}/resultado`: Obter o resultado da votação de uma pauta.
    - Documentação gerada automaticamente com SpringDoc OpenAPI.

## Boas Práticas Adotadas

- **Simplicidade no Design:** O projeto utiliza apenas as ferramentas e padrões necessários para atender aos requisitos.
- **Organização do Código:** Segue uma estrutura modular e limpa, com responsabilidades bem definidas.
- **Tratamento de Erros:** Exceções são tratadas com handlers globais, retornando mensagens claras e apropriadas para o cliente.
- **Logs:** Implementados com SLF4J, fornecendo informações úteis para monitoramento e depuração.
- **Mensagens de Commit:** Seguem um padrão claro e descritivo, conforme manda a especificação conventional commits.

## Configuração do Ambiente

1. Certifique-se de ter as seguintes ferramentas instaladas:
   - Java 17
   - Maven
   - Docker

2. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/desafio-backend-sicredi.git
   cd desafio-backend-sicredi
   ```

3. Instale as dependências:
   ```bash
   mvn clean install
   ```

4. Configure as variáveis de ambiente:
   - Crie um arquivo `.env` na raiz do projeto.
   - Adicione as variáveis necessárias conforme o arquivo `.env.example`.

5. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```
   
6. A aplicação estará disponível em `http://localhost:8080`.

## Documentação da API

A documentação da API é gerada automaticamente com o SpringDoc OpenAPI e pode ser acessada em:
```
http://localhost:8080/swagger-ui/index.html#
```

### Lista de CPF/CNPJ para testes de votação

A aplicação possui uma lista de CPF/CNPJ cadastrados no sistema para testes, indicando se podem ou não votar:

| CPF/CNPJ         | Pode Votar |
|------------------|------------|
| 12345678901      | Sim        |
| 98765432100      | Não        |
| 12345678000195   | Sim        |
| 98765432000100   | Não        |
| 11122233344      | Sim        |
| 55566677788      | Não        |
| 11222333000181   | Sim        |
| 99887766000199   | Não        |

Esses dados podem ser utilizados para validar as funcionalidades relacionadas ao registro de votos na aplicação.

## Testes Automatizados

O projeto inclui testes unitários, de integração e de performance para garantir a qualidade do código:

- **Testes de Mensageria:** Verificam o envio de mensagens no RabbitMQ.
- **Testes de Performance:** Avaliam a capacidade de lidar com 1000 requisições simultâneas de votos.
- **Testes de Integração:** Validam a interação entre as camadas, desde a chamado do controller até a persistência no banco de dados e retorno para o usuário.
- **Cobertura de Código:** Garantida com ferramentas de teste do Spring e geração de relatórios com o JaCoCo

Para executar os testes (unitários e de integração), utilize o comando:
```bash
  mvn test
```

Para gerar o relatório de cobertura do JaCoCo utilize os seguintes comandos:
```bash
  mvn clean test
  mvn jacoco:report
```
O relatório estará disponível em `target/site/jacoco/index.html`.

## Logs da Aplicação

Os logs são configurados para fornecer informações detalhadas sobre o funcionamento da aplicação, incluindo:


* Erros e exceções.
* Mensagens de debug para monitoramento de operações importantes.

Os arquivos gerados estão localizados na pasta `logs` na raiz do projeto.

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).

## Contato

Para dúvidas, entre em contato:
- **Email:** [mazzojp@gmail.com](mazzojp@gmail.com)
- **LinkedIn**: [linkedin.com/in/joaopmazzo/](linkedin.com/in/joaopmazzo/)
