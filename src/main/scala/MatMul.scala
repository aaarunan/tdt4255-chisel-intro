package Ex0

import chisel3._
import chisel3.util.Counter
import chisel3.experimental.MultiIOModule

class MatMul(val rowDimsA: Int, val colDimsA: Int) extends MultiIOModule {

  val io = IO(
    new Bundle {
      val dataInA = Input(UInt(32.W))
      val dataInB = Input(UInt(32.W))

      val dataOut = Output(UInt(32.W))
      val outputValid = Output(Bool())
    }
  )

  val debug = IO(
    new Bundle {
      val myDebugSignal = Output(Bool())
    }
  )

  /** Your code here
    */
  val matrixA = Module(new Matrix(rowDimsA, colDimsA)).io
  val matrixB = Module(new Matrix(rowDimsA, colDimsA)).io
  val dotProdCalc = Module(new DotProd(colDimsA)).io
  val controller = Module(new Controller(rowDimsA, colDimsA)).io

  for (ii <- 0 until rowDimsA) {
    matrixA.colIdx := controller.colIdx
    matrixA.rowIdx := controller.rowIdx

    matrixB.colIdx := controller.rowIdx
    matrixB.rowIdx := controller.colIdx

    dotProdCalc.dataInA := matrixA.dataOut
    dotProdCalc.dataInB := matrixB.dataOut

  }

  io.dataOut := dotProdCalc.dataOut
  io.

}
