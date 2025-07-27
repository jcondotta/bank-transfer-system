#!/bin/bash
set -e

echo "🚀 Creating Kinesis stream..."

awslocal kinesis create-stream \
  --stream-name bank-transfer.internal.requested \
  --shard-count 1 &

echo "✅ Kinesis stream creation requested."
