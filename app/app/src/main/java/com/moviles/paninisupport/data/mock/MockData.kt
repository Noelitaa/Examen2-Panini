package com.moviles.paninisupport.data.mock

import com.moviles.paninisupport.data.remote.model.TicketDto
import com.moviles.paninisupport.data.remote.model.TicketCategory
import com.moviles.paninisupport.data.remote.model.TicketPriority
import com.moviles.paninisupport.data.remote.model.TicketStatus

object MockData {

    val tickets: List<TicketDto> = listOf(
        TicketDto(
            id = "T001",
            title = "Faltante de Sobres Serie A — Zona Norte",
            description = "El punto de venta de Liberia reporta faltante de 500 sobres de la Serie A del álbum FIFA 2026. El pedido fue despachado hace 5 días pero no ha llegado. Clientes realizando reclamos constantes.",
            priority = TicketPriority.CRITICAL,
            status = TicketStatus.OPEN,
            provider = "LogiDistrib CR",
            category = TicketCategory.INVENTORY,
            createdAt = "2026-05-20T08:30:00"
        ),
        TicketDto(
            id = "T002",
            title = "Retraso en Despacho — Cadena Walmart CR",
            description = "La distribución a las 14 sucursales de Walmart Costa Rica presenta retraso de 3 días. El proveedor confirma que el cargamento está en bodega pero no ha sido asignado a ruta. Pérdida de ventas estimada en ₡2.5M.",
            priority = TicketPriority.HIGH,
            status = TicketStatus.IN_PROGRESS,
            provider = "Distribuidora Norte S.A.",
            category = TicketCategory.DISTRIBUTION,
            createdAt = "2026-05-21T10:15:00"
        ),
        TicketDto(
            id = "T003",
            title = "Defecto de Impresión — Figuras Serie B Selección México",
            description = "Se detectaron aproximadamente 1200 sobres de la Serie B con defecto de impresión en las figuras de la Selección de México. Las imágenes presentan desviación de color superior al 30%. Requiere revisión de lote completo.",
            priority = TicketPriority.HIGH,
            status = TicketStatus.OPEN,
            provider = "Impresora Segura S.A.",
            category = TicketCategory.QUALITY,
            createdAt = "2026-05-22T09:00:00"
        ),
        TicketDto(
            id = "T004",
            title = "Pérdida de Paquetes — Ruta Cartago–San José",
            description = "El transportista reporta que 8 cajas de 500 sobres cada una no llegaron al destino en la ruta Cartago–San José. El GPS del vehículo muestra desvío no autorizado. Se abrió investigación interna.",
            priority = TicketPriority.CRITICAL,
            status = TicketStatus.IN_PROGRESS,
            provider = "LogiDistrib CR",
            category = TicketCategory.LOGISTICS,
            createdAt = "2026-05-23T11:45:00"
        ),
        TicketDto(
            id = "T005",
            title = "Sobreprecio en Factura — Pedido #PAN-2026-0412",
            description = "La factura del proveedor AlmaCentro FIFA incluye un sobrecargo del 18% respecto al precio acordado en contrato. Diferencia de $4,200 USD. Se solicita nota de crédito y revisión de términos contractuales.",
            priority = TicketPriority.MEDIUM,
            status = TicketStatus.OPEN,
            provider = "AlmaCentro FIFA",
            category = TicketCategory.PROVIDER,
            createdAt = "2026-05-24T14:20:00"
        ),
        TicketDto(
            id = "T006",
            title = "Inventario Duplicado — Punto de Venta Liberia Centro",
            description = "El sistema registra inventario duplicado para el punto de venta de Liberia Centro: 800 sobres Serie C aparecen registrados dos veces. Error identificado en el proceso de sincronización del ERP.",
            priority = TicketPriority.LOW,
            status = TicketStatus.RESOLVED,
            provider = "Distribuidora Sur S.A.",
            category = TicketCategory.INVENTORY,
            createdAt = "2026-05-25T08:00:00"
        ),
        TicketDto(
            id = "T007",
            title = "Cajas Dañadas por Humedad — Almacén Central Alajuela",
            description = "El almacén central reporta 35 cajas dañadas por filtración de agua en el área de bodega. Aproximadamente 17,500 sobres comprometidos. Se requiere evaluación de seguro y reposición urgente del stock.",
            priority = TicketPriority.HIGH,
            status = TicketStatus.IN_PROGRESS,
            provider = "AlmaCentro FIFA",
            category = TicketCategory.QUALITY,
            createdAt = "2026-05-26T13:10:00"
        ),
        TicketDto(
            id = "T008",
            title = "Error en Cantidad de Pedido — Distribuidora Sur S.A.",
            description = "El pedido PO-2026-0089 fue procesado con 2,000 sobres en lugar de los 20,000 solicitados. Error atribuido a fallo tipográfico en el sistema de órdenes. Se requiere orden de corrección urgente.",
            priority = TicketPriority.MEDIUM,
            status = TicketStatus.OPEN,
            provider = "Distribuidora Sur S.A.",
            category = TicketCategory.DISTRIBUTION,
            createdAt = "2026-05-27T09:30:00"
        ),
        TicketDto(
            id = "T009",
            title = "Incumplimiento de Contrato — Stickers Edición Especial USA",
            description = "Proveedor Global Stickers no entregó el lote de 50,000 stickers de la edición especial EE.UU. en la fecha acordada (2026-05-15). Lleva 13 días de retraso sin justificación formal. Aplican penalidades contractuales.",
            priority = TicketPriority.CRITICAL,
            status = TicketStatus.OPEN,
            provider = "Proveedor Global Stickers",
            category = TicketCategory.PROVIDER,
            createdAt = "2026-05-28T15:00:00"
        ),
        TicketDto(
            id = "T010",
            title = "Retraso en Producción — Figuras Edición Dorada Copa",
            description = "La producción de las figuras de la Edición Dorada Copa sufrió retraso de 10 días por falla en una prensa de impresión. El lote fue entregado con retraso pero sin afectar la calidad final del producto.",
            priority = TicketPriority.MEDIUM,
            status = TicketStatus.CLOSED,
            provider = "Impresora Segura S.A.",
            category = TicketCategory.QUALITY,
            createdAt = "2026-05-29T10:45:00"
        )
    )
}
