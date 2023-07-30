package net.tazato.absurd.ql.types

import net.tazato.absurd.ql.traits.LazyRender

case class Field(fs: String, alias: Option[Alias] = None) extends LazyRender:
  override lazy val render =
    alias match
      case Some(a) => s"${this.fs} ${a.render}"
      case None    => this.fs
