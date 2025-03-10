/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.struts.annotation.tiger;

import org.seasar.struts.Constants;

/**
 * 外部コンテキストのスコープを表す列挙型です。
 * 
 * @author Katsuhiko Nagashima
 */
public enum ScopeType {

	/**
	 * リクエスト
	 */
	REQUEST,

	/**
	 * セッション
	 */
	SESSION,

	/**
	 * 未定義
	 */
	UNDEFINED;

	/**
	 * スコープを示す文字列を返します。
	 * 
	 * @return スコープを示す文字列
	 */
	public String getScopeMode() {
		if (this == UNDEFINED) {
			return Constants.UNDEFINED;
		}
		return toString().toLowerCase();
	}

}
