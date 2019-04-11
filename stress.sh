#!/bin/bash

export http_proxy=http://localhost:4140

while :
do
	curl -XPOST http://service-order-l5d/api/order
done