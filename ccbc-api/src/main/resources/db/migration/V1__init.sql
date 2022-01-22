-- public.accounts definition

-- Drop table

-- DROP TABLE public.accounts;

CREATE TABLE public.accounts (
                                 id varchar(255) NOT NULL,
                                 created_at timestamp NOT NULL,
                                 updated_at timestamp NOT NULL,
                                 address varchar(255) NOT NULL,
                                 CONSTRAINT accounts_pkey PRIMARY KEY (id)
);


-- public.asset_histories definition

-- Drop table

-- DROP TABLE public.asset_histories;

CREATE TABLE public.asset_histories (
                                        id varchar(255) NOT NULL,
                                        created_at timestamp NOT NULL,
                                        updated_at timestamp NOT NULL,
                                        collected_at timestamp NOT NULL,
                                        value numeric(19,2) NOT NULL,
                                        wallet_address varchar(255) NOT NULL,
                                        CONSTRAINT asset_histories_pkey PRIMARY KEY (id)
);
CREATE INDEX asset_histories_collected_at ON public.asset_histories USING btree (collected_at);


-- public.coins definition

-- Drop table

-- DROP TABLE public.coins;

CREATE TABLE public.coins (
                              id varchar(255) NOT NULL,
                              created_at timestamp NOT NULL,
                              updated_at timestamp NOT NULL,
                              address varchar(255) NOT NULL,
                              decimals int4 NOT NULL,
                              name varchar(255) NOT NULL,
                              original_symbol varchar(255) NOT NULL,
                              price float8 NOT NULL,
                              symbol varchar(255) NOT NULL,
                              symbol_image_url varchar(255) NOT NULL,
                              CONSTRAINT coins_pkey PRIMARY KEY (id)
);


-- public.questions definition

-- Drop table

-- DROP TABLE public.questions;

CREATE TABLE public.questions (
                                  id varchar(255) NOT NULL,
                                  created_at timestamp NOT NULL,
                                  updated_at timestamp NOT NULL,
                                  contents text NOT NULL,
                                  priority int4 NOT NULL,
                                  title varchar(255) NOT NULL,
                                  CONSTRAINT questions_pkey PRIMARY KEY (id)
);


-- public.release_notes definition

-- Drop table

-- DROP TABLE public.release_notes;

CREATE TABLE public.release_notes (
                                      id varchar(255) NOT NULL,
                                      created_at timestamp NOT NULL,
                                      updated_at timestamp NOT NULL,
                                      contents text NOT NULL,
                                      title varchar(255) NOT NULL,
                                      CONSTRAINT release_notes_pkey PRIMARY KEY (id)
);


-- public.definix_farms definition

-- Drop table

-- DROP TABLE public.definix_farms;

CREATE TABLE public.definix_farms (
                                      id varchar(255) NOT NULL,
                                      created_at timestamp NOT NULL,
                                      updated_at timestamp NOT NULL,
                                      address varchar(255) NOT NULL,
                                      decimals int4 NOT NULL,
                                      pid int4 NULL,
                                      coin_0_id varchar(255) NOT NULL,
                                      coin_1_id varchar(255) NOT NULL,
                                      CONSTRAINT definix_farms_pkey PRIMARY KEY (id),
                                      CONSTRAINT fk_definix_pairs_coin_0_id FOREIGN KEY (coin_0_id) REFERENCES coins(id),
                                      CONSTRAINT fk_definix_pairs_coin_1_id FOREIGN KEY (coin_1_id) REFERENCES coins(id)
);


-- public.dex_pairs definition

-- Drop table

-- DROP TABLE public.dex_pairs;

CREATE TABLE public.dex_pairs (
                                  id varchar(255) NOT NULL,
                                  created_at timestamp NOT NULL,
                                  updated_at timestamp NOT NULL,
                                  address varchar(255) NOT NULL,
                                  decimals int4 NOT NULL,
                                  "type" varchar(255) NOT NULL,
                                  coin_0_id varchar(255) NOT NULL,
                                  coin_1_id varchar(255) NOT NULL,
                                  CONSTRAINT dex_pairs_pkey PRIMARY KEY (id),
                                  CONSTRAINT fk_dex_pairs_coin_0_id FOREIGN KEY (coin_0_id) REFERENCES coins(id),
                                  CONSTRAINT fk_dex_pairs_coin_1_id FOREIGN KEY (coin_1_id) REFERENCES coins(id)
);


-- public.klayswap_pools definition

-- Drop table

-- DROP TABLE public.klayswap_pools;

CREATE TABLE public.klayswap_pools (
                                       id varchar(255) NOT NULL,
                                       created_at timestamp NOT NULL,
                                       updated_at timestamp NOT NULL,
                                       address varchar(255) NOT NULL,
                                       decimals int4 NOT NULL,
                                       coin_0_id varchar(255) NOT NULL,
                                       coin_1_id varchar(255) NOT NULL,
                                       CONSTRAINT klayswap_pools_pkey PRIMARY KEY (id),
                                       CONSTRAINT fk_klayswap_pools_coin_0_id FOREIGN KEY (coin_0_id) REFERENCES coins(id),
                                       CONSTRAINT fk_klayswap_pools_coin_1_id FOREIGN KEY (coin_1_id) REFERENCES coins(id)
);


-- public.price_histories definition

-- Drop table

-- DROP TABLE public.price_histories;

CREATE TABLE public.price_histories (
                                        id varchar(255) NOT NULL,
                                        created_at timestamp NOT NULL,
                                        updated_at timestamp NOT NULL,
                                        collected_at timestamp NOT NULL,
                                        price float8 NOT NULL,
                                        coin_id varchar(255) NOT NULL,
                                        CONSTRAINT price_histories_pkey PRIMARY KEY (id),
                                        CONSTRAINT fk_price_histories_coin_id FOREIGN KEY (coin_id) REFERENCES coins(id)
);
CREATE INDEX price_histories_coin_id_collected_at ON public.price_histories USING btree (coin_id, collected_at);
CREATE INDEX price_histories_collected_at ON public.price_histories USING btree (collected_at);


-- public.klayfi_commodity definition

-- Drop table

-- DROP TABLE public.klayfi_commodity;

CREATE TABLE public.klayfi_commodity (
                                         id varchar(255) NOT NULL,
                                         created_at timestamp NOT NULL,
                                         updated_at timestamp NOT NULL,
                                         address varchar(255) NOT NULL,
                                         decimals int4 NOT NULL,
                                         "type" varchar(255) NOT NULL,
                                         coin_id varchar(255) NULL,
                                         pool_id varchar(255) NULL,
                                         CONSTRAINT klayfi_commodity_pkey PRIMARY KEY (id),
                                         CONSTRAINT fk_klayfi_commodity_coin_id FOREIGN KEY (coin_id) REFERENCES coins(id),
                                         CONSTRAINT fk_klayfi_commodity_info_histories_pool_id FOREIGN KEY (pool_id) REFERENCES klayswap_pools(id)
);