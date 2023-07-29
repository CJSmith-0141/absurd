package net.tazato.surreal.ql.types

trait LazyRender:
  lazy val render: String

object LazyRender:
  def renderSeq(lrl: Seq[LazyRender]) =
    lrl.map(_.render).mkString(", ")
