#Desenvolvedor (a) de Busca - Desafio

##Descrição

O objetivo deste desafio é avaliar seu conhecimento sobre algoritmos de indexação e de recuperação de informação, avaliar a qualidade do seu código em termos de
escrita, padronização e performance na utilização de recursos e estruturas de dados.


Seu desafio é criar um programa que busque por uma sentença em todos estes
arquivos e exiba quantos e quais arquivos possuem esta palavra chave. A ordenação
dos arquivos deve ser feito em ordem crescente e alfabética. O seu programa deve ser executado a partir da linha de comando do terminal (Linux/MacOS) e deve permitir que a sentença que deverá ser encontrada seja passada como parâmetro de execução.

Pré-condições:
- Você deve ter o código coberto por testes unitários e deve utilizar somente ferramentas nativas da linguagem que você escolher no desenvolvimento do algoritmo (Node.js ou Java). 
- É desejável que o código esteja versionado, preferencialmente usando GIT. Para os testes você pode utilizar frameworks não nativos
- Seu projeto deve ser entregue com um arquivo README.md que contenha as instruções de instalação, execução de testes e execução do programa.
- Lembre-se que as palavras digitadas em um sistema de busca não necessariamente estão na mesma ordem em que elas estão salvas nos arquivos. 
- O "match" das palavras deve utilizar o critério "AND". Ou seja, ambas devem existir no arquivo, independente da ordem ou das outras palavras separarem múltiplos termos.
- O processo de busca deve ser executado em até 0.01ms.
- Você pode pré-processar e preparar a massa de arquivos em um outro script.

##Instalação
Requisitos:
- Java 11 ou superior (https://www.java.com/pt-BR/download/ie_manual.jsp?locale=pt_BR)
- Maven 3.6.3 ou superior (https://maven.apache.org/download.cgi)

Construindo a aplicação:
- Executar na raiz do projeto:
```bash
mvn clean install
```

##Executando a aplicação
Passo a passo:
- Ir para a pasta `target` criada:
```bash
cd target
```
- Para criar o arquivo de índice (executar antes do comando de busca):
```bash
java -jar search.jar --createindex <diretorio>
```
Sendo `<diretorio>`, a pasta que contém os arquivos a serem indexados.

- Para executar a busca (após a criação do índice):
```bash
java -jar search.jar "palavras"
```
Sendo `"palavras"`, as palavras que serão buscadas pelo sistema, separadas por espaço.

##Resultado dos testes
Passo a passo:
- Abrir o arquivo `index.html` para ver a cobertura de testes (**jacoco**):
```bash
open target/site/jacoco/index.html
```

##Javadoc
Passo a passo:
- Abrir o arquivo `index.html` para ver a documentação da API (**javadoc**):
```bash
open target/site/apidocs/index.html
```


##Arquitetura

Padrão de arquitetura:
- MVP (Model View Presenter)

