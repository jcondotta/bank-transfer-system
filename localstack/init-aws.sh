#!/bin/bash
set -e

awslocal sqs create-queue \
  --queue-name bank-transfer-internal-requested.fifo \
  --attributes '{
    "FifoQueue": "true",
    "ContentBasedDeduplication": "false"
  }'

#
#echo "🚀 Creating Kinesis streams..."
#
#awslocal kinesis create-stream \
#  --stream-name bank-transfer.internal.requested \
#  --shard-count 1 &
#
#awslocal kinesis create-stream \
#  --stream-name bank-transfer.internal.failed \
#  --shard-count 1 &
#
#awslocal kinesis create-stream \
#  --stream-name bank-transfer.internal.completed \
#  --shard-count 1 &
#
#echo "✅ Kinesis streams creation done."
