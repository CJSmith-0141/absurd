/*
 * Copyright 2023 Connor James Smith
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.tazato.absurd.ql.statements

import net.tazato.absurd.ql.*
import net.tazato.absurd.ql.traits.Render

enum Info(val id: String):
  case KV                      extends Info("KV")
  case NS                      extends Info("NS")
  case NAMESPACE               extends Info("NAMESPACE")
  case DB                      extends Info("DB")
  case DATABASE                extends Info("DATABASE")
  case SCOPE(val name: String) extends Info("SCOPE")
  case TABLE(val name: String) extends Info("TABLE")
  lazy val render: String = Info.render(this)

object Info extends Render[Info]:
  import Info.*
  override def render(x: Info): String =
    x match
      case KV | NS | NAMESPACE | DB | DATABASE =>
        s"INFO FOR ${x.id};"
      case SCOPE(y) =>
        s"INFO FOR ${x.id} $y;"
      case TABLE(y) =>
        s"INFO FOR ${x.id} $y;"
