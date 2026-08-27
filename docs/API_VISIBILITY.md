# API Visibility — convenção de classificação (@api.*)

Cópia da convenção da casa, mantida aqui de propósito para você não precisar
clonar outro repositório para entender o que o CI desta lib faz. A versão de
referência vive no `anymarket-marketplace-sdk`; se as duas divergirem, aquela
manda — mas divergir é raro, e ler uma delas basta.

## O que se aplica a ESTA lib, hoje

Quase nada do que está abaixo. Esta lib **não tem tags `@api.*` e não precisa
ter**: ela não é lib de contrato, e nenhum DTO daqui é serializado direto na
API pública. O que o CI publica a cada jar é um manifesto **só com os enums**,
porque classe sem tag nenhuma não entra no manifesto.

Isso importa porque os valores de enum daqui **aparecem na documentação
pública** dos serviços que consomem a lib — `RETRY`, `TRANSLATING` e
`REMOVING`, de `TransmissionStatus`, saem em `transmissionStatus`. O fonte
desta lib não está no worktree desses serviços, então sem o manifesto o
verificador do docs-sync barra esses valores por falta de lastro.

Consequências práticas de quem mexe aqui:

- **Valor novo de enum entra sozinho** no próximo jar. Nada a fazer.
- **Enum removido ou renomeado** pode quebrar a documentação de um consumidor:
  o valor continua publicado na spec e passa a não ter lastro. Se for
  intencional, avise o time que mantém `anymarket/docs`.

O resto deste documento é a convenção completa, para o dia em que esta lib
virar lib de contrato — ou para quando você precisar entender o que os
serviços e a `anymarket-marketplace-sdk` estão fazendo.

## Por que a convenção existe

A documentação pública da API é gerada a partir do código. Como o fonte de uma
lib não está presente no CI dos consumidores, a visibilidade viaja num
**manifesto de release**: `<artifact>-<versão>-visibility.json`, publicado no
Nexus junto do artefato, extraído das tags Javadoc por parser determinístico
(motor no repositório docs-sync, `manifest.py`). O consumidor lê o manifesto da
versão pinada no pom dele — visibilidade e dependência andam juntas, sem drift.

Publicar um campo interno é irreversível. **Na dúvida, classifique como
interno.** Nunca decida "público" por suposição — mudar interno→publico é
decisão de negócio.

## A regra

Todo campo de instância de classe DTO tem exatamente UMA tag no Javadoc:

```java
/**
 * Id do vínculo adicional no marketplace.   <- descrição opcional, pt-BR
 *
 * @api.publico
 */
private String idInMarketplace;

/** @api.interno */
private Integer bindIndex;
```

- **Um campo por declaração.** `private String a, b;` impede decisão individual.
- A tag vale independente do modificador (`private`, `public`, package-private).
- Nome JSON: se houver `@JsonProperty`, o manifesto registra o nome JSON real —
  é ele que conta para a doc.
- Herança: campo do pai é tagueado uma vez, no pai. O manifesto achata a cadeia
  (campo do pai aparece no filho com `declaredIn` apontando o declarante), e
  subclasse de classe tagueada é DTO — os campos próprios dela também precisam
  de tag.
- Classe de payload NOVA: tague todos os campos na criação. Classe sem NENHUMA
  tag é tratada como não-DTO e fica fora do manifesto — se ela for usada em
  contrato público de um consumidor, o pipeline de docs dele falha por falta de
  lastro (fail-closed), então a ausência aparece, mas aparece TARDE. Tague na
  origem.
- Campos de inner class `Builder` são plumbing de construção: não exigem tag.
- Enums: as constantes são exportadas no manifesto e validam os valores que a
  doc pública pode listar. Valor novo de enum entra automaticamente no próximo
  release. **É o único mecanismo que esta lib usa hoje.**
- Campos sensíveis (`oi`, `token`, `accessKey`, `secret`, `password`,
  credenciais em geral): SEMPRE `@api.interno`, sem exceção.
- Duplicatas entre módulos (mesma classe em dois módulos do reator): os
  vereditos por campo devem casar entre as cópias.

## Enforcement (onde a regra morde)

1. **Geração do manifesto**: aborta com erro nomeado se uma classe com tags
   tiver campo sem tag — release nunca sai com manifesto parcial. Atenção ao
   MOMENTO: esse job roda junto do build que publica o jar, não no merge
   request. Campo sem tag passa no MR calado e só estoura na publicação.
   O guard de MR dos serviços não cobre uma lib: ele deriva a superfície a
   partir de endpoint público, e numa lib não há controller, então lista zero
   arquivos. Revisão humana do MR é a primeira barreira real.
2. **Consumidor**: o pipeline de docs dos serviços falha fail-closed quando um
   tipo de lib em contrato público não tem lastro no manifesto da versão pinada.
3. **Exceção do consumidor**: um serviço pode declarar
   `@api.campo.interno Tipo.campo` para restringir localmente um campo público —
   isso NÃO altera a lib; a classificação da origem continua sendo a base.

## Propriedade que existe só como getter

O guard cobra tag em campo; o Jackson serializa por getter. Getter público
sem campo correspondente emite propriedade sem classificação — marque com
`@api.prop.publico <nomeJson>` ou `@api.prop.interno <nomeJson>` no Javadoc
do getter. Getter derivado de campo já tagueado herda a visibilidade dele.

## Tags que só existem em SERVIÇO

Numa lib elas são ruído — aparecem aqui só para você reconhecê-las ao ler o
código de um consumidor:

- `@api.spec <nome>` — em qual spec do repo de docs um controller entra.
- `@api.param.publico|interno` e `@api.header.publico|interno` — classificam
  query param e header de um endpoint.
- `@api.campo.interno Tipo.campo` — a exceção do consumidor descrita acima.

## O que você nunca faz

- Nunca adiciona campo sem tag em classe DTO **de lib de contrato**.
- Nunca muda `@api.interno` para `@api.publico` sem instrução humana explícita.
- Nunca "resolve" a falta de manifesto de um consumidor editando a doc pública —
  o caminho é release da lib com as tags corretas.

## O job nesta lib

Opt-in duplo, já ligado no `.gitlab-ci.yml`:

```yaml
VISIBILITY_MANIFEST_ENABLED: "true"
VISIBILITY_MANIFEST_ARTIFACT: "anymarket-sdk-all"
```

O artifactId não é o do pom raiz de propósito: a raiz é agregador e ninguém a
declara como dependência — o consumidor pina `anymarket-sdk-all`. O job roda
nos mesmos gatilhos do build que publica o jar (master, `release/X.Y`,
develop), porque o manifesto é artefato irmão: versão publicada sem manifesto
deixa o consumidor sem lastro.

Contexto completo do desenho: `plano-docs-publicas.md` no repositório de
documentações da equipe.
