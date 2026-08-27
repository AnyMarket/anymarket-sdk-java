# AGENTS — guia para agentes de IA neste repositório

## Manifesto de visibilidade de API

Esta lib **não usa tags `@api.*` e não precisa usar**. Mas o CI dela publica,
junto de cada jar que vai pro Nexus (master, `release/X.Y` e develop), um
manifesto de visibilidade
(`<artifactId>-<versão>-visibility.json`), e esse manifesto sai **só com os
enums** — porque classe sem tag nenhuma não entra nele.

Por que isso existe: os valores de enum daqui aparecem na documentação pública
dos serviços que consomem a lib (por exemplo `RETRY`, `TRANSLATING` e
`REMOVING` em `transmissionStatus`). O fonte desta lib não está no worktree
desses serviços, então sem o manifesto o verificador do docs-sync barra esses
valores por falta de lastro.

Consequências práticas para quem mexe aqui:

- **Valor novo de enum entra sozinho** no próximo release. Nada a fazer.
- **Enum removido ou renomeado** pode quebrar a documentação de um consumidor:
  o valor continua publicado na spec e passa a não ter lastro. Se for
  intencional, avise o time que mantém `anymarket/docs`.
- Se algum dia esta lib virar lib de contrato (DTO consumido diretamente na
  API pública), aí sim entram as tags `@api.publico`/`@api.interno` por campo.
  A convenção completa está em `docs/API_VISIBILITY.md`, neste repositório.

O job é opt-in duplo e já está ligado no `.gitlab-ci.yml`:
`VISIBILITY_MANIFEST_ENABLED: "true"` e
`VISIBILITY_MANIFEST_ARTIFACT: "anymarket-sdk-all"` (o artefato que os
consumidores declaram no pom).

Contexto completo do desenho: `plano-docs-publicas.md` no repositório de
documentações da equipe.
