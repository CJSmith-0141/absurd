package net.tazato.absurd.ql.types

import net.tazato.absurd.ql.traits.LazyRender

case class Target(trg: String) extends LazyRender:
  override lazy val render = trg
