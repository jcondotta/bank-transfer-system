#!/bin/bash
set -e

echo "🚀 Creating Kinesis streams..."

awslocal kinesis create-stream \
  --stream-name bank-transfer.internal.requested \
  --shard-count 1 &

awslocal kinesis create-stream \
  --stream-name bank-transfer.internal.failed \
  --shard-count 1 &

awslocal kinesis create-stream \
  --stream-name bank-transfer.internal.completed \
  --shard-count 1 &

echo "✅ Kinesis streams creation done."
