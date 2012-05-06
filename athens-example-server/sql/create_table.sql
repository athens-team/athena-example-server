/*
 * Copyright 2012 Athens Team
 *
 * This file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
DROP TABLE IF EXISTS omp_user;
CREATE TABLE omp_user (
	id integer auto_increment primary key,
	email_addr varchar(255) not null,
	nickname varchar(255),
	bio TEXT,
	created_time timestamp not null DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS omp_device;
CREATE TABLE omp_device (
	id integer auto_increment primary key,
	uuid varchar(255),
	user_id integer not null,
	model varchar(255),
	created_time timestamp not null DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS omp_post;
CREATE TABLE omp_post (
	id integer auto_increment primary key,
	user_id integer not null,
	content TEXT,
	created_time timestamp not null DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS omp_comment;
CREATE TABLE omp_comment (
	id integer auto_increment primary key,
	user_id integer not null,
	post_id integer not null,
	content TEXT,
	created_time timestamp not null DEFAULT CURRENT_TIMESTAMP
);
