# MPS - Laboratórios 4 e 5 

## Padrões de Projeto

### Adapter

Foram implementados dois adapters, `util.FileInputAdapter` e `util.FileOutputAdapter` para facilitar
o uso de leitura e escrita de objetos em arquivos.

### Template Method

Foi aplicado em `business.model.Message`, que é uma classe abstrata que tem duas classes que herdam, `business.model.CommonMessage` e `business.model.VipMessage`, que têm comportamentos diferentes
para o método `getColoredText`.

### Factory Method

Aplicado a `infra.DataRepository` por meio de `infra.RepositoryFactory`. O retorno é uma interface implementada pelo `infra.DataRepository`.

### Command

Aplicado às operações com `business.model.Wall`. Há 3 operações, `Create`, `Update` e `Remove`, implementadas em `business.control.WallCommands`, mesmo local onde está a interface do Command.

### Memento

Também aplicado a `business.model.Wall`, permite desfazer uma das 3 operações citadas anteriormente.