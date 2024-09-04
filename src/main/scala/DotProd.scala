package Ex0

import chisel3._
import chisel3.util.Counter

class DotProd(val elements: Int) extends Module {

  val io = IO(
    new Bundle {
      val dataInA = Input(UInt(32.W))
      val dataInB = Input(UInt(32.W))

      val dataOut = Output(UInt(32.W))
      val outputValid = Output(Bool())
    }
  )

  val (counter, wrap) = Counter(true.B, elements)
  val accumulator = RegInit(UInt(32.W), 0.U)

  // Please don't manually implement product!
  val product = io.dataInA * io.dataInB

  accumulator := product + accumulator

  when(wrap === true.B) {
    io.outputValid := true.B
    accumulator := 0.U
  }.otherwise {
    io.outputValid := false.B
    io.dataOut := 0.U
  }
  io.dataOut := accumulator + product
}
