/*
 * Copyright © 2019 Dominokit
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
package org.dominokit.domino.ui.tabs;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.grid.flex.FlexItem;
import org.dominokit.domino.ui.utils.BaseDominoElement;

/** A component that adds a space between {@link VerticalTab}s in the {@link VerticalTabsPanel} */
public class FillItem extends BaseDominoElement<HTMLDivElement, FillItem> {

  private FlexItem<HTMLDivElement> element;

  public FillItem() {
    this.element = FlexItem.create().setFlexGrow(1);
    init(this);
  }

  /** @return new instance */
  public static FillItem create() {
    return new FillItem();
  }

  /** {@inheritDoc} */
  @Override
  public HTMLDivElement element() {
    return element.element();
  }
}
