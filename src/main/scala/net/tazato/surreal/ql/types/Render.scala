package net.tazato.surreal.ql.types

trait Render[-A]:
  def render(x: A): String
