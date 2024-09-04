package Ex0

import chisel3._
import chisel3.util.Counter
import chisel3.experimental.MultiIOModule

class Controller(val rows: Int, val cols: Int) extends MultiIOModule {
  val io = IO(
    new Bundle {
      val rowIdx = Output(UInt(32.W))
      val colIdx = Output(UInt(32.W))
      val state = Output(Bool())
    }
  )

  val (counter, wrap) = Counter(true.B, rows * cols)

  io.rowIdx := counter / rows.U

  io.colIdx := counter % cols.U

  when(wrap === true.B) {
    io.state := true.B
  }.otherwise {
    io.state := false.B
  }

}
