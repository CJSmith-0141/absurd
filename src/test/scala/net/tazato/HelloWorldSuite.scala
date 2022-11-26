package net.tazato

import cats.effect.{IO, SyncIO}
import munit.CatsEffectSuite

class HelloWorldSuite extends CatsEffectSuite {

  test("test hello world says hi") {
    HelloWorld.say().map(it => assertEquals(it, "Hello Cats!"))
  }
}
