package net.tazato.absurd.ql.types

import net.tazato.absurd.ql.traits.LazyRender

case class Cond(test: String, continuation: Option[String] = None)
    extends LazyRender:
  override lazy val render =
    continuation match
      case Some(c) => s"$c ${this.test}"
      case None    => this.test
