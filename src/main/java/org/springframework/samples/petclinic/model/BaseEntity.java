/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * test!
 */
package org.springframework.samples.petclinic.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for objects
 * needing this property.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
/*
Прежде всего, использование аннотаций в качестве нашего метода настройки-это просто удобный 
метод вместо того, чтобы справляться с бесконечным файлом конфигурации XML.
Аннотация @Id наследуется от javax.persistence.Id, указывая, что поле элемента ниже является
первичным ключом текущей сущности. Следовательно, ваш фреймворк Hibernate и spring, а также вы 
можете выполнить некоторые работы reflect на основе этой аннотации. для получения более подробной 
информации, пожалуйста, проверьте javadoc на наличие идентификатора
Аннотация @GeneratedValue предназначена для настройки способа приращения указанного столбца(поля).
 Например , при использовании Mysql вы можете указать auto_increment в определении таблицы, чтобы 
 сделать ее самоинкрементной, а затем использовать
*/ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isNew() {
		return this.id == null;
	}

}
