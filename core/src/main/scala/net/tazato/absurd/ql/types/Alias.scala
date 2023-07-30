package net.tazato.absurd.ql.types

import net.tazato.absurd.ql.traits.LazyRender

case class Alias(als: String) extends LazyRender:
  override lazy val render = s"AS ${this.als}"
