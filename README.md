# Example Microservice BFF

Exemplo arquitetura Angular + BFF + Compose

```plantuml
skinparam style strictuml
actor User
participant Front
participant BFF
participant Core
database DBJor
database MongoDB
participant "**Order**" as Order
database R102
participant Reports
collections awsS3

note over User,R102:1 - Emissão de pedido
autonumber 1.1
User->Front:Solicita emissão de um pedido
Front->BFF: POST \orders {simulationId}
BFF-\Core: POST \orders {simulationId}
activate Core
Core->DBJor: updade status {simulationId, emissaoEmAndamento}
Core->DBJor: insert tb_pedido_parameters {simulationId, reason, cd, supplier}
Core->MongoDB: db.simulationSummaryDC.find( { simulationId: "{simulationId}" } )
Core->MongoDB: db.simulationProductEntity.find( { simulationId: "{simulationId}" } )
Core->Core: split CD, fornecedor, tipoControlado, sequence
Core->Core: remove qtd < 1
Core-\Order: POST \orders {OrderRequest}
deactivate Core
activate Order
Order->R102: insert tb_pedido_compra + tb_pedido_compra_item
Order->Core: updateOrderNumber {pedidoEmitido}
deactivate Order
activate Core
Core->DBJor: saveOrderNumber And UpdateSimulationStatus {pedidoEmitido}
deactivate Core

note over User,Notify:2 - Envio de pedido
autonumber 2.1
User->Front: Solicita envio de um pedido
Front->BFF: GET /orders/{simulationId}/send
BFF-\Core: GET /orders/{simulationId}/send
activate Core
Order->Core: updateSimulationStatus {pedidosSendoEnviados}
Core->DBJor: select Orders {simulationId}
Core-\Order: GET /orders/{orderNumbers}/send
deactivate Core
activate Order
Order->R102: insert pedido_compra_rastreio
Order->Core: updateSimulationStatus {pedidoEnviado ou erroNoEnvio}
deactivate Order
activate Core
Core->DBJor: UpdateSimulationStatus {pedidoEnviado ou erroNoEnvio}
deactivate Core
``` 
