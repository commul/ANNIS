/*
 * Copyright 2015 Corpuslinguistic working group Humboldt University Berlin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package annis.gui.admin;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Button;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;
import com.vaadin.ui.declarative.Design;

/**
 * UI to edit the properties of a single user.
 *
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
@DesignRoot
public class EditSingleUser extends Panel
{

  private Label lblUser;

  private Button btSave;

  private Button btCancel;

  private PopupTwinColumnSelect groupSelector;

  private PopupTwinColumnSelect permissionSelector;

  private OptionalDateTimeField expirationSelector;

  public EditSingleUser(
    String userName,
    final Property.Transactional groups,
    final Property.Transactional permissions,
    final Property.Transactional expires,
    IndexedContainer groupsContainer)
  {
    Design.read(EditSingleUser.this);

    
    groupSelector.setSelectableContainer(groupsContainer);
    
    groups.startTransaction();
    permissions.startTransaction();
    expires.startTransaction();

    // bind the fields
    lblUser.setPropertyDataSource(new ObjectProperty<>(userName));
    groupSelector.setPropertyDataSource(groups);
    permissionSelector.setPropertyDataSource(permissions);
    expirationSelector.setPropertyDataSource(expires);

    // events
    btSave.addClickListener(new Button.ClickListener()
    {
      @Override
      public void buttonClick(Button.ClickEvent event)
      {
        groups.commit();
        permissions.commit();
        expires.commit();
        
        HasComponents parent = getParent();
        if (parent instanceof Window)
        {
          ((Window) parent).close();
        }
      }
    });

    btCancel.addClickListener(new Button.ClickListener()
    {

      @Override
      public void buttonClick(Button.ClickEvent event)
      {
        groups.rollback();
        permissions.rollback();
        expires.rollback();

        HasComponents parent = getParent();
        if (parent instanceof Window)
        {
          ((Window) parent).close();
        }
      }
    });

    expirationSelector.setCheckboxCaption("expires");

  }

  @Override
  public void attach()
  {
    super.attach();
  }

}
