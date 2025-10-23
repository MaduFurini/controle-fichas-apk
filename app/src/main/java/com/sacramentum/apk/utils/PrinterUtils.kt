package com.sacramentum.apk.com.sacramentum.apk.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.sacramentum.apk.com.sacramentum.apk.model.CartItem
import com.sunmi.peripheral.printer.SunmiPrinterService

object PrinterUtils {

    private var sunmiPrinterService: SunmiPrinterService? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            sunmiPrinterService = SunmiPrinterService.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            sunmiPrinterService = null
        }
    }

    fun initPrinterService(context: Context) {
        if (sunmiPrinterService == null) {
            val intent = Intent()
            intent.setPackage("woyou.aidlservice.jiuiv5")
            intent.action = "woyou.aidlservice.jiuiv5.IWoyouService"
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    /**
     * Imprime uma ficha separada para cada unidade vendida.
     */
    fun printProductTickets(
        context: Context,
        orderItems: List<CartItem>
    ) {
        Log.d("PrinterUtils", "Total de itens: ${orderItems.size}")

        if (sunmiPrinterService == null) {
            initPrinterService(context)
            Toast.makeText(context, "Inicializando impressora...", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            Log.d("PrinterUtils", "=== INICIANDO IMPRESSÃO DE FICHAS ===")
            Log.d("PrinterUtils", "Total de itens: ${orderItems.size}")

            for (item in orderItems) {
                val name = item.product.name.uppercase()
                val quantity = item.quantity

                Log.d("PrinterUtils", "Imprimindo: $name | Qtd: $quantity")

                for (i in 1..quantity) {
                    Log.d("PrinterUtils", "  -> Imprimindo ficha $i de $quantity")

                    // Cabeçalho
                    sunmiPrinterService?.printText("-----------------------------\n", null)
                    sunmiPrinterService?.printText("      FICHA DE PRODUTO\n", null)
                    sunmiPrinterService?.printText("-----------------------------\n\n", null)

                    // Nome do produto
                    sunmiPrinterService?.setAlignment(1, null)
                    sunmiPrinterService?.printText("🧾 $name\n", null)
                    sunmiPrinterService?.printText("Ficha nº $i de $quantity\n\n", null)

                    // Rodapé
                    sunmiPrinterService?.printText("-----------------------------\n", null)
                    sunmiPrinterService?.printText("Data:\n", null)
                    sunmiPrinterService?.printText("SACRAMENTUM BAR\n\n\n\n", null)

                    sunmiPrinterService?.lineWrap(3, null)

                    Log.d("PrinterUtils", "  -> Ficha $i enviada!")
                }
            }

            Log.d("PrinterUtils", "=== IMPRESSÃO CONCLUÍDA ===")
            Toast.makeText(context, "Fichas enviadas para impressão!", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Log.e("PrinterUtils", "ERRO ao imprimir: ${e.message}", e)
            e.printStackTrace()
            Toast.makeText(context, "Erro ao imprimir fichas: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Imprime o recibo completo do pedido.
     */
    fun printReceipt(
        context: Context,
        orderItems: List<com.sacramentum.apk.com.sacramentum.apk.model.CartItem>,
        totalPrice: Double,
        payment: String,
        observations: String,
        cashReceived: Double?,
        change: Double?
    ) {
        if (sunmiPrinterService == null) {
            initPrinterService(context)
            Toast.makeText(context, "Inicializando impressora...", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            sunmiPrinterService?.setAlignment(1, null)
            sunmiPrinterService?.printText("==== SACRAMENTUM BAR ====\n\n", null)
            sunmiPrinterService?.setAlignment(0, null)

            sunmiPrinterService?.printText("Itens do Pedido:\n", null)
            sunmiPrinterService?.printText("-----------------------------\n", null)

            // Lista de produtos
            orderItems.forEach { item ->
                val name = item.product.name
                val qty = item.quantity
                val price = item.product.price
                val total = qty * price
                sunmiPrinterService?.printText(
                    "$name x$qty  -  R$ %.2f\n".format(total),
                    null
                )
            }

            sunmiPrinterService?.printText("-----------------------------\n", null)
            sunmiPrinterService?.printText("Total: R$ %.2f\n".format(totalPrice), null)
            sunmiPrinterService?.printText("Pagamento: $payment\n", null)

            if (cashReceived != null) {
                sunmiPrinterService?.printText("Recebido: R$ %.2f\n".format(cashReceived), null)
            }
            if (change != null) {
                sunmiPrinterService?.printText("Troco: R$ %.2f\n".format(change), null)
            }

            if (observations.isNotBlank()) {
                sunmiPrinterService?.printText("\nObs: $observations\n", null)
            }

            sunmiPrinterService?.printText(
                "\nData:\n\n",
                null
            )

            sunmiPrinterService?.setAlignment(1, null)
            sunmiPrinterService?.printText("Obrigado pela preferência!\n\n\n", null)
            sunmiPrinterService?.lineWrap(3, null)

            Toast.makeText(context, "Recibo impresso com sucesso!", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Erro ao imprimir recibo: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
