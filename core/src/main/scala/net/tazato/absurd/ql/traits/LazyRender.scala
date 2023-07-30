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

package net.tazato.absurd.ql.traits

/** like Render but expressed as a value
  */
trait LazyRender:
  lazy val render: String

object LazyRender:

  /** @param lrl
    *   seqence of LazyRender
    * @return
    *   String formed by rendering all LazyRender in Seq
    */
  def renderSeq(lrl: Seq[LazyRender]): String =
    lrl.map(_.render).mkString(", ")

  def renderSeqOption(lrl: Seq[LazyRender]): Option[String] =
    lrl match
      case x if x.isEmpty => None
      case _              => Some(renderSeq(lrl))
